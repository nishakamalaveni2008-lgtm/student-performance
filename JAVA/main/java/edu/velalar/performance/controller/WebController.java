package edu.velalar.performance.controller;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.IOException;
import edu.velalar.performance.model.*;
import edu.velalar.performance.repository.*;
import edu.velalar.performance.service.ResultService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class WebController {
    @Value("${spring.datasource.password}")
private String dbPassword;
 private final StudentRepository students; private final SubjectRepository subjects; private final ResultService results; private final MarkRepository marks;
 public WebController(StudentRepository s,SubjectRepository sub,ResultService r,MarkRepository m){students=s;subjects=sub;results=r;marks=m;}
 @GetMapping("/") public String home(Model model){
  model.addAttribute("students",students.findAll()); model.addAttribute("subjects",subjects.findAll());
  model.addAttribute("marks",results.allMarks()); model.addAttribute("studentCount",students.count());
  model.addAttribute("subjectCount",subjects.count()); model.addAttribute("markCount",marks.count()); return "index";
 }
 @PostMapping("/students") public String addStudent(@RequestParam String studentId,@RequestParam String name,@RequestParam String department,@RequestParam int yearOfStudy,RedirectAttributes ra){
  try{students.save(new Student(studentId.trim(),name.trim(),department.trim(),yearOfStudy));ra.addFlashAttribute("message","Student saved successfully.");}
  catch(Exception e){ra.addFlashAttribute("error","Could not save student. Check that the ID is unique and inputs are valid.");} return "redirect:/";
 }
 @PostMapping("/subjects") public String addSubject(@RequestParam String subjectId,@RequestParam String subjectName,@RequestParam int maxMarks,@RequestParam int passMarks,RedirectAttributes ra){
  try{if(passMarks>maxMarks)throw new IllegalArgumentException();subjects.save(new Subject(subjectId.trim(),subjectName.trim(),maxMarks,passMarks));ra.addFlashAttribute("message","Subject saved successfully.");}
  catch(Exception e){ra.addFlashAttribute("error","Could not save subject. Check unique ID and marks range.");}return "redirect:/";
 }
 @PostMapping("/marks") public String addMarks(@RequestParam String studentId,@RequestParam String subjectId,@RequestParam int internalMarks,@RequestParam int examMarks,RedirectAttributes ra){
  try{results.saveMark(studentId,subjectId,internalMarks,examMarks);ra.addFlashAttribute("message","Marks saved and result calculated.");}
  catch(Exception e){ra.addFlashAttribute("error",e.getMessage());}return "redirect:/";
 }
 @GetMapping("/students/{id}") public String studentReport(@PathVariable String id,Model model){
  model.addAttribute("student",students.findById(id).orElseThrow());model.addAttribute("marks",results.studentMarks(id));return "student-report";
 }
 @GetMapping("/api/results") @ResponseBody public Object apiResults(){return results.allMarks();}
 @GetMapping("/api/students") @ResponseBody public Object apiStudents(){return results.allStudents();}
 @GetMapping("/api/subjects") @ResponseBody public Object apiSubjects(){return results.allSubjects();}
 @PostMapping("/marks/{id}/delete") public String deleteMark(@PathVariable Long id){marks.deleteById(id);return "redirect:/";}

@PostMapping("/analysis/generate")
public String generateAnalysis(RedirectAttributes ra) {
        try {
        Path projectDir = Path.of("").toAbsolutePath();

        Path script = projectDir.resolve("python")
                .resolve("analyze_performance.py");

        if (!Files.exists(script)) {
            throw new IOException("Python script not found: " + script);
        }

        ProcessBuilder pb = new ProcessBuilder("py", script.toString());
        pb.directory(projectDir.toFile());
        pb.redirectErrorStream(true);

        Map<String, String> env = pb.environment();
        env.put("DB_HOST", "localhost");
        env.put("DB_PORT", "5432");
        env.put("DB_USER", "postgres");
        env.put("DB_NAME", "student_performance");
        env.put("DB_PASSWORD", dbPassword);

        Process process = pb.start();

        String output = new String(
                process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Python failed: " + output);
        }

        Path source = projectDir.resolve("python/output");
        Path destination = projectDir.resolve(
                "src/main/resources/static/reports"
        );

        Files.createDirectories(destination);

        String[] charts = {
            "department_average.png",
            "subject_pass_percentage.png",
            "marks_distribution.png"
        };

        for (String chart : charts) {
            Files.copy(
                source.resolve(chart),
                destination.resolve(chart),
                StandardCopyOption.REPLACE_EXISTING
            );
        }

        ra.addFlashAttribute(
            "message",
            "Python analysis completed successfully!"
        );

    } catch (Exception e) {
        ra.addFlashAttribute(
            "error",
            "Analysis failed: " + e.getMessage()
        );
    }

    return "redirect:/#analysis";
}
}
