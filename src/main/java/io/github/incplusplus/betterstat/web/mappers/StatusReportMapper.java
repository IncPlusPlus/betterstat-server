package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.StatusReport;
import io.github.incplusplus.betterstat.web.dto.StatusReportDto;
import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(StatusReportMapperDecorator.class)
public interface StatusReportMapper {

  StatusReportDto toDTO(StatusReport statusReport);

  StatusReport fromDto(StatusReportDto statusReportDto, String thermostatId);

  List<StatusReportDto> mapStatusReportsToStatusReportDto(List<StatusReport> statusReports);
}
