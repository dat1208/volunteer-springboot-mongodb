package com.volunteer.springbootmongo.models.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author "KhaPhan" on 22-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinPostModelCollection {
    private List<JoinPostModel> joinPostModelList = new ArrayList<>();
}
