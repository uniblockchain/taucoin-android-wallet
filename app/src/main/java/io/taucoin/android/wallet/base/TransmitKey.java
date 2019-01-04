/**
 * Copyright 2018 Taucoin Core Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.taucoin.android.wallet.base;

public class TransmitKey {
    public static final String SERVICE_TYPE = "service_type";

    public static class ServiceType{
        public static final String GET_HOME_DATA = "get_home_data";
        public static final String GET_SEND_DATA = "get_send_data";
        public static final String GET_BALANCE = "get_balance";
        public static final String GET_UTXO_LIST = "get_utxo_list";
        public static final String GET_RAW_TX = "get_raw_tx";
        public static final String GET_INFO = "get_info";
    }

    public static final String PRIVATE_KEY = "Privkey";
    public static final String PUBLIC_KEY = "Pubkey";
    public static final String ADDRESS = "Address";

    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String HOW_IMPORT_KEY_URL = "https://android.taucoin.io/static/help3.html";

    public static final int PAGE_SIZE = 20;

    public static class TxResult{
        public static final String FAILED = "Failed";
        public static final String SUCCESSFUL = "Successful";
        public static final String CONFIRMING = "Confirming";
    }
}