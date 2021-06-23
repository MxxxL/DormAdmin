package com.kaiyu.dao;

import com.kaiyu.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Repository
public interface StudentDao {

    List<Student> getStudentByName(@Param("studentName") String studentName);

    int saveStudent(Student student);

    int editStudent(Student student);

    int deleteStudents(@Param("ids") List<Integer> ids);
}
