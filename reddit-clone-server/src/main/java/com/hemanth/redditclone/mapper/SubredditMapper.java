package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.SubredditDto;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    SubredditMapper MAPPER = Mappers.getMapper(SubredditMapper.class);

    SubredditDto mapToDto(Subreddit subreddit);

    @Mapping(source = "subredditDto.id", target = "id")
    @Mapping(source = "subredditDto.name", target = "name")
    @Mapping(source = "subredditDto.description", target = "description")
    @Mapping(source = "user", target = "user")
    Subreddit mapToSubreddit(SubredditDto subredditDto, User user);
}
