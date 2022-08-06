
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
public class CoreErrorData implements Serializable {

    private static final long serialVersionUID = 3106967564025989917L;

    @Expose
    private String responsecode;
    @Expose
    private String responsemessage;

}
