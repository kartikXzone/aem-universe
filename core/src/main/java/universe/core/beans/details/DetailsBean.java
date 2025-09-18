package universe.core.beans.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsBean {

    private String firstName;
    private String lastName;
    private List<ParentDetailsBean> parentDetailsBeanList;

}