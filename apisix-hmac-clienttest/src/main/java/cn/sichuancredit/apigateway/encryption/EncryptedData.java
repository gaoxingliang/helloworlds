package cn.sichuancredit.apigateway.encryption;

public class EncryptedData {
    String encryptKey;
    String data;
    String dataStatus;

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EncryptedData{" +
                "encryptKey='" + encryptKey + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
