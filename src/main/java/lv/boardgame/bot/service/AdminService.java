package lv.boardgame.bot.service;

import lv.boardgame.bot.model.Admin;

import java.util.List;

public interface AdminService {

	Admin saveAdmin(Admin admin);

	List<Admin> findAllAdmins();

	void deleteByUsername(String username);

	List<String> findAllAdminUsernameList();
}
