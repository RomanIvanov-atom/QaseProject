package api.models.suite;

import api.models.base.Requestable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSuiteRq implements Requestable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("preconditions")
    @Expose
    private String preconditions;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
}
