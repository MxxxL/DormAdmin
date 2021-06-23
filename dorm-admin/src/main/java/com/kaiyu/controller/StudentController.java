package com.kaiyu.controller;

import com.kaiyu.entity.Student;
import com.kaiyu.model.ResponseMsg;
import com.kaiyu.model.ResponsePage;
import com.kaiyu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/22
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public ResponsePage getStudentPage(@RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size,
                                       @RequestParam(name = "name", required = false) String studentName) {
        return studentService.getStudentByName(page, size, studentName);
    }

    @PostMapping("/change")
    public ResponseMsg saveStudent(@RequestBody Student student) {
        if (studentService.saveStudent(student) >= 1) {
            return ResponseMsg.ok("添加成功！");
        } else {
            return ResponseMsg.error("添加失败！");
        }
    }

    @PutMapping("/change")
    public ResponseMsg editStudent(@RequestBody Student student) {
        if (studentService.editStudent(student) >= 1) {
            return ResponseMsg.ok("修改成功！");
        } else {
            return ResponseMsg.error("修改失败！");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseMsg deleteStudent(@PathVariable("id") Integer id) {
        if (studentService.deleteStudent(id) >= 1) {
            return ResponseMsg.ok("删除成功！");
        } else {
            return ResponseMsg.error("删除失败！");
        }
    }

    @DeleteMapping("/")
    public ResponseMsg deleteStudents(List<Student> students) {
        if (studentService.deleteStudents(students) >= 1) {
            return ResponseMsg.ok("删除成功！");
        } else {
            return ResponseMsg.error("删除失败！");
        }
    }
}
