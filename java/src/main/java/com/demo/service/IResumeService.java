package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.resume.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IResumeService {
    /**
     * 履歴経歴書一覧取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<SearchResumeDTO>> getResumeList(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> insertResume(InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> updateResume(InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<InsertResumeDTO> getResume(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> deleteResume(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> exportResume(SearchResumeDTO searchResumeDTO);

    ServerResponse<List<GetResumeVerDTO>> getResumeVerList(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<GetUserDTO>> getUserList(SearchUserDTO searchUserDTO, HttpServletRequest request, HttpServletResponse response);

}
