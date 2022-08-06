
package com.flutterwave.middleware.notification.coreresponse;

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
public class CoreAuthErrorResponse implements Serializable {

    private static final long serialVersionUID = -6155089636814391856L;

    @Expose
    private CoreErrorData data;
    @Expose
    private String status;

}
