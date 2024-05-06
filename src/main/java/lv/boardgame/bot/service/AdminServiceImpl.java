package lv.boardgame.bot.service;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.Admin;
import lv.boardgame.bot.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;

	@Override
	public Admin saveAdmin(final Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public List<Admin> findAllAdmins() {
		return adminRepository.findAll();
	}

	@Override
	public void deleteByUsername(final String username) {
		adminRepository.deleteByUsername(username);
	}

	public List<String> findAllAdminUsernameList() {
		return findAllAdmins().stream().map(Admin::getUsername).toList();
	}
}
