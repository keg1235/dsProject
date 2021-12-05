package com.sara.project.main;

import com.sara.project.pageSet.PageSet;
import com.sara.project.pageSet.PageSetRepository;
import com.sara.project.user.dto.UserLoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private PageSetRepository pageSetRepository;

    @RequestMapping("company_list")
    public ModelAndView viewcompany_list(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("company_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("company_registration")
    public ModelAndView viewcompany_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("company_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }




    @RequestMapping("dcp_registration")
    public ModelAndView viewdcp_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcp_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("dcpset_registration")
    public ModelAndView dcpset_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        mav.setViewName("dcpset_registration");

        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcpset_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("dcpset_registration/{Id}")
    public ModelAndView dcpset_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("ddc_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcpset_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("drawgroup_registration")
    public ModelAndView datagroup_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");


        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("drawgroup_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("drawgroup_list")
    public ModelAndView datagroup_list(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");

        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("drawgroup_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("drawgroup_registration/{Id}")
    public ModelAndView datagroup_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("datagroup_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("drawgroup_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("setting")
    public ModelAndView viewSetting(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("setting");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("dcpset_list")
    public ModelAndView dcpset_list(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcpset_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }
    @RequestMapping("dcp_registration/{Id}")
    public ModelAndView viewdcp_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("ddc_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcp_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("ddc_registration")
    public ModelAndView viewddc_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("ddc_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("dcu_registration")
    public ModelAndView viewdcu_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcu_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }
    @RequestMapping("dcu_registration/{Id}")
    public ModelAndView viewdcu_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("ddc_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcu_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("chart/{Id}")
    public ModelAndView viewchart_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("chart 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("chart");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("grape/{Id}")
    public ModelAndView grape_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("grape 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("grape");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("settimer_list")
    public ModelAndView viewssettimer_list(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("settimer_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }



    @RequestMapping("scheduling_registration")
    public ModelAndView viewschduing_registration(
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("scheduling_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }
    @RequestMapping("scheduling_registration/{Id}")
    public ModelAndView viewschduing_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("ddc_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("scheduling_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("ddc_registration/{Id}")
    public ModelAndView viewddc_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("ddc_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("ddc_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("draggable")
    public ModelAndView viewdraggable(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("draggable");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("equipment_list")
    public ModelAndView viewequipment_list(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("equipment_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("gateway_registration")
    public ModelAndView viewgateway_registration(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("gateway_registration");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("gateway_registration/{Id}")
    public ModelAndView viewgateway_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("gateway_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("gateway_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("index-admin")
    public ModelAndView viewindexadmin(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index-admin");
        return mav;

    }

    @RequestMapping("index-member")
    public ModelAndView viewindexmember(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index-member");
        return mav;

    }

    @RequestMapping("")
    public ModelAndView viewindexuser(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index-user");


        return mav;

    }

    @RequestMapping("mamagemet_home")
    public ModelAndView viewmamagemet_home(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("mamagemet_home");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }


    @RequestMapping("mamagemet_list")
    public ModelAndView viewmamagemet_list(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("mamagemet_list");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("member_list")
    public ModelAndView viewmember_list(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("member_list");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }
    @RequestMapping("groupsetting/{Id}")
    public ModelAndView viewGroupSetting(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("viewGroupSetting 들어옴"+Id);
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if(pageSet != null){
                mav.addObject("companyName",pageSet.getCompany());
            }
            mav.setViewName("groupsetting");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }
    @RequestMapping("groupsetting")
    public ModelAndView viewGroupSetting(HttpServletRequest request) throws Exception {
        System.out.println("viewGroupSetting 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if(pageSet != null){
                mav.addObject("companyName",pageSet.getCompany());
            }
            mav.setViewName("groupsetting");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("group_list")
    public ModelAndView viewGroup_list(HttpServletRequest request) throws Exception {
        System.out.println("group_list 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("group_list");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }


    @RequestMapping("member_registration/{Id}")
    public ModelAndView viewmember_registration(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("member_registration 들어옴"+Id);
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("member_registration");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("member_registration")
    public ModelAndView viewmember_registration(HttpServletRequest request) throws Exception {
        System.out.println("member_registration 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("member_registration");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("message_alram_list")
    public ModelAndView viewmessage_alram_list(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("message_alram_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }


    @RequestMapping("message_list")
    public ModelAndView viewmessage_list(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("message_list");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("message_registration")
    public ModelAndView viewmessage_registration(HttpServletRequest request) throws Exception {
        System.out.println("message_registration 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("message_registration");
            mav.addObject("user",userLoginResponseDto);
        }
        return mav;

    }

    @RequestMapping("message_send_member")
    public ModelAndView viewmessage_send_member(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if(pageSet != null){
                mav.addObject("companyName",pageSet.getCompany());
            }
            mav.setViewName("message_send_member");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }


    @RequestMapping("modal")
    public ModelAndView viewmodal(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("modal");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("site_registration")
    public ModelAndView viewsite_registration(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if(pageSet != null){
                mav.addObject("companyName",pageSet.getCompany());
            }
            mav.setViewName("site_registration");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("user_home")
    public ModelAndView viewuser_home(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("user_home");
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }


    @RequestMapping("user_main")
    public ModelAndView viewuser_main(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("user_main");
            mav.addObject("user",userLoginResponseDto);
        }


        return mav;

    }

    @RequestMapping("report")
    public ModelAndView viewdcp_report(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("dcp_report");
            mav.addObject("user",userLoginResponseDto);
        }


        return mav;

    }


    @RequestMapping("graph_home/{Id}")
    public ModelAndView graph_home(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("graph_home 들어옴"+Id);
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("graph_home");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }

    @RequestMapping("user_main/{Id}")
    public ModelAndView viewuser_main(@PathVariable("Id") Long Id,HttpServletRequest request) throws Exception {
        System.out.println("user_main 들어옴"+Id);
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("user_main");
            mav.addObject("id",Id);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }


    @RequestMapping("main")
    public ModelAndView view_main(HttpServletRequest request) throws Exception {
        System.out.println("Main 들어옴");
        ModelAndView mav = new ModelAndView();

        HttpSession httpSession = request.getSession(true);

        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if(pageSet != null){
                mav.addObject("companyName",pageSet.getCompany());
                mav.addObject("graphSet",pageSet.getGraph());
                mav.addObject("graphDay",pageSet.getGraphDay());
                mav.addObject("graphMin",pageSet.getGraphMin());
                mav.addObject("graphMax",pageSet.getGraphMax());

            }
            mav.setViewName("main");
            mav.addObject("user",userLoginResponseDto);
        }


        return mav;

    }
    @RequestMapping("monitoring/draw")
    public ModelAndView viewmonitoringdraw(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("monitoring");
            mav.addObject("draw",true);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }
    @RequestMapping("monitoring")
    public ModelAndView viewmonitoring(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView();
        HttpSession httpSession = request.getSession(true);
        UserLoginResponseDto userLoginResponseDto = (UserLoginResponseDto) httpSession.getAttribute("USER");
        if (userLoginResponseDto == null|| !userLoginResponseDto.isLoginFlg()){
            mav.setViewName("index-user");
        }else{
            mav.setViewName("monitoring");
            mav.addObject("draw",false);
            mav.addObject("user",userLoginResponseDto);
        }

        return mav;

    }



    @RequestMapping("test")
    public ModelAndView viewuser_test() throws Exception {
        System.out.println("test 들어옴");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        return mav;

    }
}
