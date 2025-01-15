package api.models.testCase;

import api.models.base.Requestable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTestCaseRq implements Requestable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("preconditions")
    @Expose
    private String preconditions;
    @SerializedName("postconditions")
    @Expose
    private String postconditions;
    @SerializedName("severity")
    @Expose
    private Integer severity;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("behavior")
    @Expose
    private Integer behavior;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("is_flaky")
    @Expose
    private Integer isFlaky;
    @SerializedName("automation")
    @Expose
    private Integer automation;
    @SerializedName("status")
    @Expose
    private Integer status;
}

