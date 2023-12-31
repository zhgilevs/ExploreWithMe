package ru.practicum.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.category.entity.Category;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.event.entity.Event;
import ru.practicum.event.entity.EventState;
import ru.practicum.event.entity.Location;
import ru.practicum.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.category.dto.CategoryMapper.toCategoryDto;
import static ru.practicum.event.dto.LocationMapper.toLocationDto;
import static ru.practicum.user.dto.UserMapper.toUserShortDto;

@UtilityClass
public class EventMapper {

    public static Event toEvent(NewEventRequestDto newEventRequestDto,
                                LocalDateTime eventDay,
                                User initiator,
                                Category category,
                                Location location) {
        Event event = Event.builder()
                .title(newEventRequestDto.getTitle())
                .annotation(newEventRequestDto.getAnnotation())
                .description(newEventRequestDto.getDescription())
                .initiator(initiator)
                .category(category)
                .location(location)
                .eventDate(eventDay)
                .build();
        event.setRequestModeration((newEventRequestDto.getRequestModeration() != null) ? newEventRequestDto.getRequestModeration() : true);
        if (!event.isRequestModeration()) {
            event.setPublishedOn(event.getCreatedOn());
        }
        event.setParticipantLimit((newEventRequestDto.getParticipantLimit() != null) ? newEventRequestDto.getParticipantLimit() : 0);
        event.setPaid((newEventRequestDto.getPaid() != null) ? newEventRequestDto.getPaid() : false);
        event.setState(EventState.PENDING);
        return event;
    }

    public static EventResponseDto toEventResponseDto(Event event, List<CommentResponseDto> comments) {
        return EventResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .initiator(toUserShortDto(event.getInitiator()))
                .category(toCategoryDto(event.getCategory()))
                .location(toLocationDto(event.getLocation()))
                .eventDate(event.getEventDate())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .participantLimit(event.getParticipantLimit())
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .comments(comments)
                .build();
    }

    public static EventShortResponseDto toEventShortResponseDto(Event event, List<CommentResponseDto> comments) {
        return EventShortResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .initiator(toUserShortDto(event.getInitiator()))
                .category(toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .comments(comments)
                .build();
    }
}