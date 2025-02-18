package api.models.project;

import api.models.base.Requestable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectRq implements Requestable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("group")
    @Expose
    private String group;
}