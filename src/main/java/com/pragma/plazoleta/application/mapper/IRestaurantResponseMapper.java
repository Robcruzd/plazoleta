package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {

    List<RestaurantListResponseDto> toRestaurantListDto(List<RestaurantModel> restaurantModel);

//    List<RestaurantListResponseDto> toRestaurantListDto(List<RestaurantModel> restaurantModel);
//
//    default Page<RestaurantListResponseDto> toRestaurantListDtoPage(Page<RestaurantModel> restaurantModelPage) {
//        List<RestaurantListResponseDto> restaurantListResponseDtoList = toRestaurantListDto(restaurantModelPage.getContent());
//        return new PageImpl<>(restaurantListResponseDtoList, restaurantModelPage.getPageable(), restaurantModelPage.getTotalElements());
//    }
}
