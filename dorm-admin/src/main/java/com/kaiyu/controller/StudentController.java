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

    @GetMapping("/list")
    public ResponsePage getStudentPage(Integer page, Integer size, String name) {
        return studentService.getStudentByName(page, size, name);
    }

    @PostMapping("/add")
    public ResponseMsg saveOrEditStudent(@RequestBody Student student) {
        if (null == student.getId()) {
            if (studentService.saveStudent(student) >= 1) {
                return ResponseMsg.ok("添加成功！");
            } else {
                return ResponseMsg.error("添加失败！");
            }
        } else {
            if (studentService.editStudent(student) >= 1) {
                return ResponseMsg.ok("修改成功！");
            } else {
                return ResponseMsg.error("修改失败！");
            }
        }
    }

    @PostMapping("/del")
    public ResponseMsg deleteStudents(@RequestBody List<Student> students) {
        if (studentService.deleteStudents(students) >= 1) {
            return ResponseMsg.ok("删除成功！");
        } else {
            return ResponseMsg.error("删除失败！");
        }
    }
}
