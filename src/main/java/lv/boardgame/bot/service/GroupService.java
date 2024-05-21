package lv.boardgame.bot.service;

import lv.boardgame.bot.model.Group;

import java.util.List;

public interface GroupService {

	Group saveGroup(Group group);

	List<Group> findAllGroups();

	void deleteGroupById(String id);

}
