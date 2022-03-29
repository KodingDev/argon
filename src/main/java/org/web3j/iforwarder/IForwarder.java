package org.web3j.iforwarder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.0.
 */
@SuppressWarnings("rawtypes")
public class IForwarder extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_EXECUTE = "execute";

    @Deprecated
    protected IForwarder(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IForwarder(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IForwarder(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IForwarder(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> execute(ForwardRequest req, byte[] signature) {
        final Function function = new Function(
                FUNC_EXECUTE, 
                Arrays.<Type>asList(req, 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IForwarder load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IForwarder(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IForwarder load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IForwarder(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IForwarder load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IForwarder(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IForwarder load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IForwarder(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IForwarder> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IForwarder.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IForwarder> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IForwarder.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IForwarder> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IForwarder.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IForwarder> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IForwarder.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ForwardRequest extends DynamicStruct {
        public String from;

        public String to;

        public BigInteger value;

        public BigInteger gas;

        public BigInteger nonce;

        public byte[] data;

        public ForwardRequest(String from, String to, BigInteger value, BigInteger gas, BigInteger nonce, byte[] data) {
            super(new org.web3j.abi.datatypes.Address(160, from), 
                    new org.web3j.abi.datatypes.Address(160, to), 
                    new org.web3j.abi.datatypes.generated.Uint256(value), 
                    new org.web3j.abi.datatypes.generated.Uint256(gas), 
                    new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                    new org.web3j.abi.datatypes.DynamicBytes(data));
            this.from = from;
            this.to = to;
            this.value = value;
            this.gas = gas;
            this.nonce = nonce;
            this.data = data;
        }

        public ForwardRequest(Address from, Address to, Uint256 value, Uint256 gas, Uint256 nonce, DynamicBytes data) {
            super(from, to, value, gas, nonce, data);
            this.from = from.getValue();
            this.to = to.getValue();
            this.value = value.getValue();
            this.gas = gas.getValue();
            this.nonce = nonce.getValue();
            this.data = data.getValue();
        }
    }
}
