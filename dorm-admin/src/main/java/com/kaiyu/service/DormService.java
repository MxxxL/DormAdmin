package com.kaiyu.service;

import com.github.pagehelper.PageInfo;
import com.kaiyu.dao.DormDao;
import com.kaiyu.entity.Dorm;
import com.kaiyu.model.ResponsePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Service
public class DormService {

    @Autowired
    private DormDao dormDao;

    public List<Dorm> getDormAll() {
        return dormDao.getDormAll();
    }

    public ResponsePage getDormPage(Integer page, Integer size, String dormName) {
        startPage(page, size);
        List<Dorm> dorms = dormDao.getDormByName(dormName);
        PageInfo<Dorm> pageInfo = new PageInfo<>(dorms);
        ResponsePage responsePage = new ResponsePage();
        responsePage.setData(dorms);
        responsePage.setTotal(pageInfo.getTotal());
        return responsePage;
    }

    public int saveDorm(Dorm dorm) {
        return dormDao.saveDorm(dorm);
    }

    public int editDorm(Dorm dorm) {
        return dormDao.editDorm(dorm);
    }

    public int deleteDorms(List<Dorm> dorms) {
        List<Integer> ids = new ArrayList<>();
        for (Dorm dorm : dorms) {
            ids.add(dorm.getId());
        }
        return dormDao.deleteDorms(ids);
    }
}
