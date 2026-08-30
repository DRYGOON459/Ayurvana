package com.ayurvana.dto.request;

import com.ayurvana.model.enums.FavoriteType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFavoriteRequest {

    @NotNull(message = "Item type is required")
    private FavoriteType itemType;

    @NotNull(message = "Item ID is required")
    private UUID itemId;
}
