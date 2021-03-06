package yj.capstone.aerofarm.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import yj.capstone.aerofarm.domain.device.Model;

@Getter
@Setter
public class DeviceAdminListResponseDto {
    private Long deviceId;
    private String ipAddress;
    private String macAddress;
    private String model;
    private String uuid;
    private String owner;
    private String plant;

    @QueryProjection
    public DeviceAdminListResponseDto(Long deviceId, String ipAddress, String macAddress, Model model, String uuid, String owner, String plant) {
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.model = model.getName();
        this.uuid = uuid;
        this.owner = owner;
        this.plant = plant;
    }
}
