## Blockchain Sample

Sample application of using the <a href="https://blockchain.info/api">Blockchain API</a> (MVP architecture).

# Functionality

Application contains one function: 
* Exchange rates for Bitcoin

# ScreenShots
<div>
    <img src="/scrs/1.png"</img> 
    <img src="/scrs/2.png"</img>
</div>

# Service Information
Instruction of the installing API service: https://github.com/blockchain/service-my-wallet-v3#installation

In class `Constants` type your API key:
``` */
   public abstract class Constants {
       public static final String API_KEY = "YOUR-API-KEY";
   }
```

App using <a href="https://github.com/blockchain/api-v1-client-java">java</a> library from Blockchain.

In `build.gradle`:
```
    dependencies {
        compile 'info.blockchain:api:1.1.4'
    }
```

Example use methods from it library .All methods in main thread. For background thread used Rx:


#### Exchange Currency
```
    fun getExchangeRates(): Observable<Map<String, Currency>> {
        return Observable.fromCallable<Map<String, Currency>> { ExchangeRates.getTicker(Constants.API_KEY) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
    }
```

For get access need API key.
* Receive Payments API V2: https://api.blockchain.info/v2/apikey/request

# Developers

* [Ravi Kumar](https://github.com/Ravi-KBIHM)
