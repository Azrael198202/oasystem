package com.demo.service;
import com.demo.common.ServerResponse;
import com.demo.entity.PaidHolidayCorrect;
import com.demo.pojo.paidHolidayCorrect.DefaultPaidHolidayCorrectDTO;
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IPaidHolidayCorrectService {
    ServerResponse<String> PaidHolidayCorrect(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<DefaultPaidHolidayCorrectDTO>>getPaidHolidayCorrect(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request, HttpServletResponse response);
}
