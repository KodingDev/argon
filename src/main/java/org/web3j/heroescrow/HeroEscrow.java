package org.web3j.heroescrow;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.StaticArray5555;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
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
public class HeroEscrow extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50600054610100900460ff166200002f5760005460ff161562000039565b6200003962000107565b620000ca576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201527f647920696e697469616c697a6564000000000000000000000000000000000000606482015260840160405180910390fd5b600054610100900460ff16158015620000ed576000805461ffff19166101011790555b801562000100576000805461ff00191690555b5062000134565b60006200011f306200012560201b620012531760201c565b15905090565b6001600160a01b03163b151590565b61259c80620001446000396000f3fe608060405234801561001057600080fd5b50600436106101215760003560e01c80636c19e783116100ad5780639a1e9c33116100715780639a1e9c33146102e7578063bdaf4a6f146102fa578063d21cbce914610368578063d2e4426a1461037d578063f2fde38b1461039057600080fd5b80636c19e7831461027f5780636e68dbeb14610292578063715018a6146102a457806375e39f26146102ac5780638da5cb5b146102cc57600080fd5b80632cd8d4d7116100f45780632cd8d4d71461020f5780633633152114610225578063485cc95514610238578063514a0a0b1461024b5780636816560a1461025e57600080fd5b806301ffc9a7146101265780630700037d1461015f578063150b7a02146101ce5780631e83409a146101fa575b600080fd5b61014a610134366004611dfc565b6001600160e01b0319166301ffc9a760e01b1490565b60405190151581526020015b60405180910390f35b6101a261016d366004611e35565b61167f6020526000908152604090205463ffffffff8116906001600160601b03600160201b8204811691600160801b90041683565b6040805163ffffffff90941684526001600160601b039283166020850152911690820152606001610156565b6101e16101dc366004611e94565b6103a3565b6040516001600160e01b03199091168152602001610156565b61020d610208366004611e35565b6103d1565b005b61020d61021d366004611f77565b505050505050565b61020d610233366004611ffc565b61049b565b61020d610246366004612053565b61079d565b61020d61025936600461208c565b610915565b61027161026c3660046120b5565b6109d2565b604051908152602001610156565b61020d61028d366004611e35565b610a04565b61020d6102a03660046120e1565b5050565b61020d610a51565b6102bf6102ba36600461210d565b610a87565b6040516101569190612179565b6097546040516001600160a01b039091168152602001610156565b6102716102f5366004611e35565b610b3e565b61167e546103319063ffffffff808216916001600160601b03600160201b8204811692600160801b83041691600160a01b90041684565b6040805163ffffffff95861681526001600160601b0394851660208201529490921691840191909152166060820152608001610156565b610370610caa565b6040516101569190612187565b61020d61038b3660046121c6565b610d52565b61020d61039e366004611e35565b6111b8565b60006001600160a01b0386163014156103c45750630a85bd0160e11b6103c8565b5060005b95945050505050565b6103dc600080611262565b60006103ea33600080611409565b33600090815261167f60205260409081902080546fffffffffffffffffffffffff000000001916905560c954905163a9059cbb60e01b81526001600160a01b0385811660048301526001600160601b039390931660248201819052935091169063a9059cbb906044016020604051808303816000875af1158015610472573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104969190612286565b505050565b600260335414156104f35760405162461bcd60e51b815260206004820152601f60248201527f5265656e7472616e637947756172643a207265656e7472616e742063616c6c0060448201526064015b60405180910390fd5b6002603355610501816115bd565b6001600160a01b03811630141561053f5760405162461bcd60e51b8152602060048201526002602482015261455360f01b60448201526064016104ea565b6000805b8381101561076957600085858381811061055f5761055f6122a8565b905060200201359050336001600160a01b031660cb826115b38110610586576105866122a8565b01546201000090046001600160a01b0316146105c95760405162461bcd60e51b8152602060048201526002602482015261453560f01b60448201526064016104ea565b60ca54604051632142170760e11b81523060048201526001600160a01b03868116602483015260448201849052909116906342842e0e90606401600060405180830381600087803b15801561061d57600080fd5b505af1158015610631573d6000803e3d6000fd5b50505050600060cb826115b3811061064b5761064b6122a8565b015461ffff16905061065d81856122d4565b6040805160c081018252600080825260208201819052918101829052606081018290526080810182905260a081019190915290945060cb836115b381106106a6576106a66122a8565b825191018054602084015160408501516060860151608087015160a09097015163ffffffff16600160e01b026001600160e01b0361ffff988916600160d01b02166001600160d01b03928916600160c01b0261ffff60c01b19948a16600160b01b029490941663ffffffff60b01b196001600160a01b0390961662010000026001600160b01b0319909716999098169890981794909417929092169490941793909317929092169290921717905550819050610761816122ec565b915050610543565b5061077d610776826116ef565b6000611262565b6107913361078a836116ef565b6000611409565b50506001603355505050565b600054610100900460ff166107b85760005460ff16156107bc565b303b155b61081f5760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b60648201526084016104ea565b600054610100900460ff16158015610841576000805461ffff19166101011790555b610849611758565b61085161177f565b6108596117ae565b6001600160a01b0383166108945760405162461bcd60e51b8152602060048201526002602482015261045360f41b60448201526064016104ea565b6001600160a01b0382166108cf5760405162461bcd60e51b8152602060048201526002602482015261045360f41b60448201526064016104ea565b60c980546001600160a01b038086166001600160a01b03199283161790925560ca8054928516929091169190911790558015610496576000805461ff0019169055505050565b6097546001600160a01b0316331461093f5760405162461bcd60e51b81526004016104ea90612307565b678ac7230489e80000816001600160601b0316106109845760405162461bcd60e51b8152602060048201526002602482015261453160f01b60448201526064016104ea565b61098d426116ef565b61167e80546001600160601b03909316600160a01b026001600160a01b0363ffffffff93909316600160801b02929092166001600160801b0390931692909217179055565b61168060205281600052604060002081815481106109ef57600080fd5b90600052602060002001600091509150505481565b6097546001600160a01b03163314610a2e5760405162461bcd60e51b81526004016104ea90612307565b61168180546001600160a01b0319166001600160a01b0392909216919091179055565b6097546001600160a01b03163314610a7b5760405162461bcd60e51b81526004016104ea90612307565b610a8560006117dd565b565b6040805160c081018252600080825260208201819052918101829052606081018290526080810182905260a081019190915260cb826115b38110610acd57610acd6122a8565b6040805160c081018252929091015461ffff80821684526001600160a01b03620100008304166020850152600160b01b8204811692840192909252600160c01b810482166060840152600160d01b8104909116608083015263ffffffff600160e01b9091041660a082015292915050565b6040805160808101825261167e5463ffffffff80821683526001600160601b03600160201b8084048216602080870191909152600160801b808604851687890152600160a01b90950483166060808801919091526001600160a01b038916600090815261167f835288812089519283018a5254958616825292850484169181019190915293909204169382019390935282610bd8426116ef565b90506000836040015182610bec919061233c565b63ffffffff1690508015610c6257835163ffffffff1615610c625783516060850151610c539163ffffffff1690610c2c906001600160601b031684612361565b610c369190612380565b85602001516001600160601b0316610c4e91906122d4565b61182f565b6001600160601b031660208501525b82604001518460200151610c7691906123a2565b8351610c88919063ffffffff166123c2565b8360200151610c9791906123f1565b6001600160601b03169695505050505050565b610cb2611d8f565b604080516202b660810190915260cb6115b36000835b82821015610d49576040805160c0810182528584015461ffff80821683526201000082046001600160a01b0316602080850191909152600160b01b8304821694840194909452600160c01b820481166060840152600160d01b8204166080830152600160e01b900463ffffffff1660a0820152825260019092019101610cc8565b50505050905090565b60026033541415610da55760405162461bcd60e51b815260206004820152601f60248201527f5265656e7472616e637947756172643a207265656e7472616e742063616c6c0060448201526064016104ea565b6002603355868514610dde5760405162461bcd60e51b8152602060048201526002602482015261229960f11b60448201526064016104ea565b8263ffffffff16421115610e195760405162461bcd60e51b8152602060048201526002602482015261453360f01b60448201526064016104ea565b610e5688888888338830604051602001610e399796959493929190612452565b604051602081830303815290604052805190602001208383611897565b610e875760405162461bcd60e51b8152602060048201526002602482015261453760f01b60448201526064016104ea565b610e90846115bd565b6001600160a01b038416301415610ece5760405162461bcd60e51b8152602060048201526002602482015261114d60f21b60448201526064016104ea565b6000805b8881101561117f5760008a8a83818110610eee57610eee6122a8565b60ca546040516331a9108f60e11b8152602092909202939093013560048201819052935033926001600160a01b03169150636352211e90602401602060405180830381865afa158015610f45573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610f6991906124ac565b6001600160a01b031614610fa45760405162461bcd60e51b8152602060048201526002602482015261453560f01b60448201526064016104ea565b60ca54604051632142170760e11b8152336004820152306024820152604481018390526001600160a01b03909116906342842e0e90606401600060405180830381600087803b158015610ff657600080fd5b505af115801561100a573d6000803e3d6000fd5b50505050506040518060c0016040528061103b8a8a8581811061102f5761102f6122a8565b90506020020135611904565b61ffff1681526001600160a01b0388166020820152600060408201819052606082018190526080820181905260a09091015260cb8b8b84818110611081576110816122a8565b905060200201356115b38110611099576110996122a8565b825191018054602084015160408501516060860151608087015160a09097015163ffffffff16600160e01b026001600160e01b0361ffff988916600160d01b02166001600160d01b03928916600160c01b0261ffff60c01b19948a16600160b01b029490941663ffffffff60b01b196001600160a01b0390961662010000026001600160b01b03199097169990981698909817949094179290921694909417939093179290921692909217179055878782818110611159576111596122a8565b905060200201358261116b91906122d4565b915080611177816122ec565b915050610ed2565b5061119361118c826116ef565b6001611262565b6111a7856111a0836116ef565b6001611409565b505060016033555050505050505050565b6097546001600160a01b031633146111e25760405162461bcd60e51b81526004016104ea90612307565b6001600160a01b0381166112475760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016104ea565b611250816117dd565b50565b6001600160a01b03163b151590565b6040805160808101825261167e5463ffffffff80821683526001600160601b03600160201b830481166020850152600160801b830490911693830193909352600160a01b9004909116606082015260006112bb426116ef565b905060008260400151826112cf919061233c565b63ffffffff169050801561134d57825163ffffffff161561134057825160608401516113319163ffffffff169061130f906001600160601b031684612361565b6113199190612380565b84602001516001600160601b0316610c4e91906122d4565b6001600160601b031660208401525b63ffffffff821660408401525b831561137357848360000181815161136591906124c9565b63ffffffff1690525061138f565b8483600001818151611385919061233c565b63ffffffff169052505b5050805161167e8054602084015160408501516060909501516001600160601b03908116600160a01b026001600160a01b0363ffffffff978816600160801b02166001600160801b0392909316600160201b026001600160801b031990941696909516959095179190911793909316929092171790555050565b6001600160a01b038316600090815261167f60209081526040808320815160608082018452915463ffffffff80821683526001600160601b03600160201b808404821685890152600160801b938490048216858801908152875160808101895261167e54808616825292830484169981018a905294820490931696840196909652600160a01b909504909416928101929092529151919290916114ab916123a2565b82516114bd919063ffffffff166123c2565b82602001516114cc91906123f1565b6001600160601b0390811660208085019190915282015116604083015263ffffffff85161561153757831561151b57848260000181815161150d91906124c9565b63ffffffff16905250611537565b848260000181815161152d919061233c565b63ffffffff169052505b506001600160a01b038516600090815261167f6020908152604091829020835181549285015193909401516001600160601b03908116600160801b026bffffffffffffffffffffffff60801b19918516600160201b026001600160801b031990941663ffffffff9096169590951792909217919091169290921790915590509392505050565b803b63ffffffff8116156102a057604051630a85bd0160e11b8152306004820181905260248201526001604482015260806064820152600060848201526001600160a01b0383169063150b7a029060a4016020604051808303816000875af1925050508015611649575060408051601f3d908101601f19168201909252611646918101906124e8565b60015b6116aa573d808015611677576040519150601f19603f3d011682016040523d82523d6000602084013e61167c565b606091505b5060405162461bcd60e51b8152602060048201526002602482015261115560f21b60448201526064016104ea565b6001600160e01b03198116630a85bd0160e11b146104965760405162461bcd60e51b8152602060048201526002602482015261115560f21b60448201526064016104ea565b600063ffffffff8211156117545760405162461bcd60e51b815260206004820152602660248201527f53616665436173743a2076616c756520646f65736e27742066697420696e203360448201526532206269747360d01b60648201526084016104ea565b5090565b600054610100900460ff16610a855760405162461bcd60e51b81526004016104ea90612505565b600054610100900460ff166117a65760405162461bcd60e51b81526004016104ea90612505565b610a85611967565b600054610100900460ff166117d55760405162461bcd60e51b81526004016104ea90612505565b610a85611995565b609780546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b60006001600160601b038211156117545760405162461bcd60e51b815260206004820152602660248201527f53616665436173743a2076616c756520646f65736e27742066697420696e203960448201526536206269747360d01b60648201526084016104ea565b61168154604080516020601f85018190048102820181019092528381526000926001600160a01b0316916118f2919086908690819084018382808284376000920191909152506118ec92508991506119c59050565b90611a18565b6001600160a01b031614949350505050565b600061ffff8211156117545760405162461bcd60e51b815260206004820152602660248201527f53616665436173743a2076616c756520646f65736e27742066697420696e203160448201526536206269747360d01b60648201526084016104ea565b600054610100900460ff1661198e5760405162461bcd60e51b81526004016104ea90612505565b6001603355565b600054610100900460ff166119bc5760405162461bcd60e51b81526004016104ea90612505565b610a85336117dd565b6040517f19457468657265756d205369676e6564204d6573736167653a0a3332000000006020820152603c8101829052600090605c01604051602081830303815290604052805190602001209050919050565b6000806000611a278585611a3e565b91509150611a3481611aae565b5090505b92915050565b600080825160411415611a755760208301516040840151606085015160001a611a6987828585611c69565b94509450505050611aa7565b825160401415611a9f5760208301516040840151611a94868383611d56565b935093505050611aa7565b506000905060025b9250929050565b6000816004811115611ac257611ac2612550565b1415611acb5750565b6001816004811115611adf57611adf612550565b1415611b2d5760405162461bcd60e51b815260206004820152601860248201527f45434453413a20696e76616c6964207369676e6174757265000000000000000060448201526064016104ea565b6002816004811115611b4157611b41612550565b1415611b8f5760405162461bcd60e51b815260206004820152601f60248201527f45434453413a20696e76616c6964207369676e6174757265206c656e6774680060448201526064016104ea565b6003816004811115611ba357611ba3612550565b1415611bfc5760405162461bcd60e51b815260206004820152602260248201527f45434453413a20696e76616c6964207369676e6174757265202773272076616c604482015261756560f01b60648201526084016104ea565b6004816004811115611c1057611c10612550565b14156112505760405162461bcd60e51b815260206004820152602260248201527f45434453413a20696e76616c6964207369676e6174757265202776272076616c604482015261756560f01b60648201526084016104ea565b6000807f7fffffffffffffffffffffffffffffff5d576e7357a4501ddfe92f46681b20a0831115611ca05750600090506003611d4d565b8460ff16601b14158015611cb857508460ff16601c14155b15611cc95750600090506004611d4d565b6040805160008082526020820180845289905260ff881692820192909252606081018690526080810185905260019060a0016020604051602081039080840390855afa158015611d1d573d6000803e3d6000fd5b5050604051601f1901519150506001600160a01b038116611d4657600060019250925050611d4d565b9150600090505b94509492505050565b6000806001600160ff1b03831681611d7360ff86901c601b6122d4565b9050611d8187828885611c69565b935093505050935093915050565b604051806202b66001604052806115b3905b6040805160c08101825260008082526020808301829052928201819052606082018190526080820181905260a08201528252600019909201910181611da15790505090565b6001600160e01b03198116811461125057600080fd5b600060208284031215611e0e57600080fd5b8135611e1981611de6565b9392505050565b6001600160a01b038116811461125057600080fd5b600060208284031215611e4757600080fd5b8135611e1981611e20565b60008083601f840112611e6457600080fd5b50813567ffffffffffffffff811115611e7c57600080fd5b602083019150836020828501011115611aa757600080fd5b600080600080600060808688031215611eac57600080fd5b8535611eb781611e20565b94506020860135611ec781611e20565b935060408601359250606086013567ffffffffffffffff811115611eea57600080fd5b611ef688828901611e52565b969995985093965092949392505050565b60008083601f840112611f1957600080fd5b50813567ffffffffffffffff811115611f3157600080fd5b6020830191508360208260051b8501011115611aa757600080fd5b803561ffff81168114611f5e57600080fd5b919050565b803563ffffffff81168114611f5e57600080fd5b60008060008060008060a08789031215611f9057600080fd5b863567ffffffffffffffff811115611fa757600080fd5b611fb389828a01611f07565b9097509550611fc6905060208801611f4c565b9350611fd460408801611f4c565b9250611fe260608801611f4c565b9150611ff060808801611f63565b90509295509295509295565b60008060006040848603121561201157600080fd5b833567ffffffffffffffff81111561202857600080fd5b61203486828701611f07565b909450925050602084013561204881611e20565b809150509250925092565b6000806040838503121561206657600080fd5b823561207181611e20565b9150602083013561208181611e20565b809150509250929050565b60006020828403121561209e57600080fd5b81356001600160601b0381168114611e1957600080fd5b600080604083850312156120c857600080fd5b82356120d381611e20565b946020939093013593505050565b600080604083850312156120f457600080fd5b8235915061210460208401611f63565b90509250929050565b60006020828403121561211f57600080fd5b5035919050565b805161ffff90811683526020808301516001600160a01b0316908401526040808301518216908401526060808301518216908401526080808301519091169083015260a09081015163ffffffff16910152565b60c08101611a388284612126565b621046408101818360005b6115b38110156121bd576121a7838351612126565b60c0929092019160209190910190600101612192565b50505092915050565b60008060008060008060008060a0898b0312156121e257600080fd5b883567ffffffffffffffff808211156121fa57600080fd5b6122068c838d01611f07565b909a50985060208b013591508082111561221f57600080fd5b61222b8c838d01611f07565b909850965060408b0135915061224082611e20565b81955061224f60608c01611f63565b945060808b013591508082111561226557600080fd5b506122728b828c01611e52565b999c989b5096995094979396929594505050565b60006020828403121561229857600080fd5b81518015158114611e1957600080fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b600082198211156122e7576122e76122be565b500190565b6000600019821415612300576123006122be565b5060010190565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b600063ffffffff83811690831681811015612359576123596122be565b039392505050565b600081600019048311821515161561237b5761237b6122be565b500290565b60008261239d57634e487b7160e01b600052601260045260246000fd5b500490565b60006001600160601b0383811690831681811015612359576123596122be565b60006001600160601b03808316818516818304811182151516156123e8576123e86122be565b02949350505050565b60006001600160601b03808316818516808303821115612413576124136122be565b01949350505050565b81835260006001600160fb1b0383111561243557600080fd5b8260051b8083602087013760009401602001938452509192915050565b60a08152600061246660a08301898b61241c565b828103602084015261247981888a61241c565b6001600160a01b03968716604085015263ffffffff95909516606084015250509216608090920191909152949350505050565b6000602082840312156124be57600080fd5b8151611e1981611e20565b600063ffffffff808316818516808303821115612413576124136122be565b6000602082840312156124fa57600080fd5b8151611e1981611de6565b6020808252602b908201527f496e697469616c697a61626c653a20636f6e7472616374206973206e6f74206960408201526a6e697469616c697a696e6760a81b606082015260800190565b634e487b7160e01b600052602160045260246000fdfea2646970667358221220a8b07305308cc3728c92439ac633bb18e6beb739f928ae802a3125547df1e3dd64736f6c634300080c0033";

    public static final String FUNC_CHECKUSERREWARDS = "checkUserRewards";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_EXTENDRENTALPERIOD = "extendRentalPeriod";

    public static final String FUNC_GETALLHEROESINFO = "getAllHeroesInfo";

    public static final String FUNC_GETHEROINFO = "getHeroInfo";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REWARDS = "rewards";

    public static final String FUNC_REWARDSPERLEVEL = "rewardsPerlevel";

    public static final String FUNC_SETREWARDS = "setRewards";

    public static final String FUNC_SETSIGNER = "setSigner";

    public static final String FUNC_STAKE = "stake";

    public static final String FUNC_STAKEDHEROES = "stakedHeroes";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNSTAKE = "unstake";

    public static final String FUNC_UPDATERENT = "updateRent";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected HeroEscrow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HeroEscrow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HeroEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HeroEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> checkUserRewards(String user) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKUSERREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> claim(String to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> extendRentalPeriod(BigInteger tokenId, BigInteger _rentableUntil) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXTENDRENTALPERIOD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.generated.Uint32(_rentableUntil)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllHeroesInfo() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLHEROESINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray5555<HeroInfo>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<HeroInfo> getHeroInfo(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETHEROINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<HeroInfo>() {}));
        return executeRemoteCallSingleValueReturn(function, HeroInfo.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String wrld, String herogalaxy) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, wrld), 
                new org.web3j.abi.datatypes.Address(160, herogalaxy)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> onERC721Received(String operator, String from, BigInteger tokenId, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> rewards(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint96>() {}, new TypeReference<Uint96>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> rewardsPerlevel() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDSPERLEVEL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint96>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint96>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setRewards(BigInteger rate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint96(rate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSigner(String _signer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> stake(List<BigInteger> tokenIds, List<BigInteger> levels, String stakeTo, BigInteger _maxTimestamp, byte[] _signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(tokenIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(levels, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, stakeTo), 
                new org.web3j.abi.datatypes.generated.Uint32(_maxTimestamp), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> stakedHeroes(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STAKEDHEROES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unstake(List<BigInteger> tokenIds, String unstakeTo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNSTAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(tokenIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, unstakeTo)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateRent(List<BigInteger> tokenIds, BigInteger _deposit, BigInteger _rentalPerDay, BigInteger _minRentDays, BigInteger _rentableUntil) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATERENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(tokenIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint16(_deposit), 
                new org.web3j.abi.datatypes.generated.Uint16(_rentalPerDay), 
                new org.web3j.abi.datatypes.generated.Uint16(_minRentDays), 
                new org.web3j.abi.datatypes.generated.Uint32(_rentableUntil)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HeroEscrow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HeroEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HeroEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HeroEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HeroEscrow load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HeroEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HeroEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HeroEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HeroEscrow> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HeroEscrow.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<HeroEscrow> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HeroEscrow.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HeroEscrow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HeroEscrow.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HeroEscrow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HeroEscrow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class HeroInfo extends StaticStruct {
        public BigInteger level;

        public String owner;

        public BigInteger deposit;

        public BigInteger rentalPerDay;

        public BigInteger minRentDays;

        public BigInteger rentableUntil;

        public HeroInfo(BigInteger level, String owner, BigInteger deposit, BigInteger rentalPerDay, BigInteger minRentDays, BigInteger rentableUntil) {
            super(new org.web3j.abi.datatypes.generated.Uint16(level), 
                    new org.web3j.abi.datatypes.Address(160, owner), 
                    new org.web3j.abi.datatypes.generated.Uint16(deposit), 
                    new org.web3j.abi.datatypes.generated.Uint16(rentalPerDay), 
                    new org.web3j.abi.datatypes.generated.Uint16(minRentDays), 
                    new org.web3j.abi.datatypes.generated.Uint32(rentableUntil));
            this.level = level;
            this.owner = owner;
            this.deposit = deposit;
            this.rentalPerDay = rentalPerDay;
            this.minRentDays = minRentDays;
            this.rentableUntil = rentableUntil;
        }

        public HeroInfo(Uint16 level, Address owner, Uint16 deposit, Uint16 rentalPerDay, Uint16 minRentDays, Uint32 rentableUntil) {
            super(level, owner, deposit, rentalPerDay, minRentDays, rentableUntil);
            this.level = level.getValue();
            this.owner = owner.getValue();
            this.deposit = deposit.getValue();
            this.rentalPerDay = rentalPerDay.getValue();
            this.minRentDays = minRentDays.getValue();
            this.rentableUntil = rentableUntil.getValue();
        }
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
