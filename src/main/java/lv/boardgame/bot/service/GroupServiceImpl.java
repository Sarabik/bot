package lv.boardgame.bot.service;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.Group;
import lv.boardgame.bot.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	@Override
	public Group saveGroup(final Group group) {
		return groupRepository.save(group);
	}

	@Override
	public List<Group> findAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public void deleteGroupById(final String id) {
		groupRepository.deleteById(id);
	}
}
