
package com.flutterwave.middleware.notification.coreresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ValidationData implements Serializable {

    private static final long serialVersionUID = 6654348027879375478L;

    @Expose
    private String amount;
    @Expose
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fixedamount;
    @Expose
    private String merchantname;

    @Override
    public String toString() {
        return "ValidationData{" +
                "amount='" + amount + '\'' +
                ", fixedamount='" + fixedamount + '\'' +
                ", merchantname='" + merchantname + '\'' +
                '}';
    }
}
