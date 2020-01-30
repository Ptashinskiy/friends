package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.AllNonFriendUsersInfo;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.ReadFileException;
import com.skysoft.friends.db.repositories.UsersDbService;
import com.skysoft.friends.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersDbService usersDbService;

    @Override
    @Transactional
    public UserInfo getUserInfoByUserName(String userName) {
        UserEntity user = usersDbService.getUserByUserName(userName);
        return user.getUserInfo();
    }

    @Override
    @Transactional
    public AllNonFriendUsersInfo getAllNonFriendsUsersInfo(String currentUserName) {
        List<UserInfo> result = usersDbService.getAllNonFriendUsers(currentUserName).stream()
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
        return new AllNonFriendUsersInfo(result);
    }

    @Override
    @Transactional
    public UpdatedUserInfo updateUserInfo(String currentUserName, UserParametersToUpdate parametersToUpdate) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        return currentUser.updateInfo(parametersToUpdate);
    }

    @Override
    @Transactional
    public void uploadUserIcon(String currentUserName, MultipartFile file) {
        mediaTypeFilter(Objects.requireNonNull(file.getContentType()));
        Path path = Paths.get("user icons", currentUserName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw ReadFileException.invalidFile();
        }
    }

    private void mediaTypeFilter(String fileMediaType) {
        if (!fileMediaType.equals(MediaType.IMAGE_JPEG_VALUE) && !fileMediaType.equals(MediaType.IMAGE_PNG_VALUE)) {
            throw ReadFileException.notSupportedMediaType();
        }
    }
}
