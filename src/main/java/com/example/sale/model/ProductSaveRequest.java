package com.example.sale.model;

import com.example.sale.dao.entity.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record ProductSaveRequest(

        Long id,

        @NotBlank
        String name,

        String category,

        String description,

        String image,

        String specImage,

        List<PropertiesRecord> properties,

        List<String> relationProduct,

        String video,

        List<String> loadImage,

        String detail,

        String secondaryTitle

) {

    @SneakyThrows
    public ProductEntity convert(ObjectMapper mapper) {
        var entity = new ProductEntity();
        BeanUtils.copyProperties(this, entity);
        return entity.setProperties(mapper.writeValueAsString(this.properties))
                .setRelationProduct(mapper.writeValueAsString(this.relationProduct))
                .setLoadImage(mapper.writeValueAsString(this.loadImage));
    }

}
