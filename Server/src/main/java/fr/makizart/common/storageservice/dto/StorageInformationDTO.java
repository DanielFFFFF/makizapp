package fr.makizart.common.storageservice.dto;

public record StorageInformationDTO(
    long used,
    long total

    ) {
    public StorageInformationDTO(long used, long total) {
        this.used = used;
        this.total = total;
    }
}
