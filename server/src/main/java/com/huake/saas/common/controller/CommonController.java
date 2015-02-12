package com.huake.saas.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huake.saas.invitation.service.InvitationService;

@Controller("commonController")
@RequestMapping("/mgr/common/query/**")
public class CommonController {

	@Autowired
	private InvitationService invitationService;

}
