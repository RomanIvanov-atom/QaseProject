package dto.testCase;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class TestCase {

    private final String title;
    private final String status;
    private final String description;
    private final String severity;
    private final String priority;
    private final String type;
    private final String layer;
    private final String behavior;
    private final String preConditions;
    private final String postConditions;
}
