
package com.flutterwave.middleware.notification.coreresponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreValidationErrorResponse implements Serializable {

    private static final long serialVersionUID = -5185687035193602364L;

    @JsonProperty("data")
    private String data;
    @Expose
    private String status;

    @Override
    public String toString() {
        return "CoreValidationErrorResponse{" +
                "data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
