package universe.core.beans.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParentDetailsBean {
    private String fatherName;
    private String motherName;
    private List<AddressBean> addressBeanList;
}

