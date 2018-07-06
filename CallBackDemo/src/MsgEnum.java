/**
 * 模拟支付返回状态，类似数据验证功能
 * @author LiGuanglong
 * @date 2018/7/5
 */
public enum MsgEnum {
    Msg1(1,1, "success"), Msg2(2, 2, "fail"), Msg3(3, 3, "success"), Msg4(4, 4, "success"),Msg5(5,5,"fail"),
    Msg6(6, 6, "success");

    private int id;
    private int orderId;
    private String content;

    MsgEnum(int id, int orderId, String content) {
        this.id = id;
        this.orderId = orderId;
        this.content = content;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static String getMsgContentFromOrderId(int orderId) {
        for (MsgEnum msgEnum : MsgEnum.values()) {
            if (msgEnum.getOrderId() == orderId) {
                return msgEnum.getContent();
            }
        }
        return "";
    }
}
