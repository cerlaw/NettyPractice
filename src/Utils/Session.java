package Utils;

import lombok.Data;

/**
 * @author zhanghongjie
 * @date 2018/11/9
 * @descrition
 */
@Data
public class Session {

    /**用户唯一标识*/
    private String userId;

    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}
