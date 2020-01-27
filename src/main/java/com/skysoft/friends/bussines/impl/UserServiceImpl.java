package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.ReadFileException;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserInfo getUserInfoByUserName(String userName) {
        UserEntity user = userRepository.findByEmailOrUserName(userName)
                .orElseThrow(() -> NotFoundException.userNotFound(userName));
        return user.getUserInfo();
    }

    @Override
    public List<UserInfo> getAllUsersInfo() {
        return userRepository.findAll()
                .stream()
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UpdatedUserInfo updateUserInfo(String userLoginParameter, UserParametersToUpdate parametersToUpdate) {
        UserEntity updatableUser = userRepository.findByEmailOrUserName(userLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(userLoginParameter));
        return updatableUser.updateInfo(parametersToUpdate);
    }

    @Override
    public void uploadUserIcon(String userLoginParameter, MultipartFile file) {
        mediaTypeFilter(Objects.requireNonNull(file.getContentType()));
        String fileName = userRepository.getUserNameByLoginParameter(userLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(userLoginParameter));
        Path path = Paths.get("user icons", fileName + "\'s icon");
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
