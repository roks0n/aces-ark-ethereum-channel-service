package com.arkaces.ark_eth_channel_service.contract;

import com.arkaces.aces_server.aces_service.contract.Contract;
import com.arkaces.ark_eth_channel_service.transfer.TransferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractMapper {
    
    private final TransferMapper transferMapper;
    
    public Contract<Results> map(ContractEntity contractEntity) {
        Contract<Results> contract = new Contract<>();
        contract.setId(contractEntity.getId());
        contract.setCorrelationId(contractEntity.getCorrelationId());
        contract.setCreatedAt(contractEntity.getCreatedAt().atOffset(ZoneOffset.UTC).toString());
        contract.setStatus(contractEntity.getStatus());
        
        Results results = new Results();
        results.setDepositArkAddress(contractEntity.getDepositArkAddress());
        results.setRecipientEthAddress(contractEntity.getRecipientEthAddress());
        results.setTransfers(
            contractEntity.getTransferEntities().stream()
                .map(transferMapper::map)
                .collect(Collectors.toList())
        );
        
        contract.setResults(results);
        
        return contract;
    }
}
