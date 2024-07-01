//package org.example.DataFromGraylogApi;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.Request;
//
//import java.awt.*;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Date;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.time.Instant;
//import java.util.Map;
//import java.util.HashMap;
//
//public class RequestDataFromGraylog {
//
//    public static void main(String[] args) throws IOException {
//
//        // Define the base URL
//        String baseUrl = "https://prod2-logs.sprinklr.com/api/search/universal/absolute";
//
//        // Construct query parameters
//        Map<String, String> params = new HashMap<>();
//        params.put("query", "status:200");
//        params.put("from", "2024-06-22T15:34:49.000Z");
//        params.put("to", "2024-06-23T00:34:49.000Z");
//        params.put("limit", "3");
//        params.put("batch_size", "500");
//        params.put("fields", "bytes_sent");
//
//        // Build the query string
//        StringBuilder query = new StringBuilder();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            if (!query.isEmpty()) {
//                query.append("&");
//            }
//            query.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            query.append("=");
//            query.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        // Construct the full URL
//        URL url = new URL(baseUrl + "?" + query.toString());
//
//
//        String reqBody = "query=status%3A200&from=2024-06-20T15%3A34%3A49.000Z&to=2024-06-21T00%3A34%3A49.000Z&limit=3&batch_size=500&fields=bytes_sent";
//
//        // Convert query string to byte array
//        byte[] postDataBytes = reqBody.getBytes(StandardCharsets.UTF_8);
//        // Create HTTP connection
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//
//        String username = "sprinklr";
//        String password = "sprinklr";
//        String auth = username + ":" + password;
//        byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
//        String encodedAuth = Base64.getEncoder().encodeToString(authBytes);
//        System.out.println(encodedAuth);
//        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
//
//        // Set cookies (replace with your actual cookie values)
//        String cookieName1 = "session";
//        String cookieValue1 = "xIqA4V8R8dfCqh8AMJfHTw|1719173081|8-_fFMW_srKmvwsCKY-KXMCmOVJ5VfhvCrcFaIw-N8ascZF2Qj8P0M0rvQM3THDoigJ40_PUGI8ssR7V0ZR7XTuvCAVLVEFHBNChE-4RYOKpT-sqT5BY4T8MRSJbZjz8uLAreno-oQzreaFGDUjaHc3pL1OwWFhDrBFSKC-sB4ysT_foMS1NYaYMCb7FT3gZ-mce4bbV0oLCuC61JthkHWPyn2loLaya-Lj1PYpyIL2F82NsC1GpH8tJ-SI2d-7MQXUlUAXIiF9VpfHaXhancg_BDqjCQ-cGApKo7mqNQCnym64VlQ_qBC3j24F64Ip4MgpLfPM_oibV6BwHq4kZPXjcaTOJQqV-FkNS-a-0ocPEZ_IgNBd25W79oyyVOLNeUPcRPgHtZLau74M2aIXEAngVgo__Ks1QtnYyd6iq68z9RKfMJBMxy5fCYhnDHYtF5uDsnH0jMn8SNPcR8ccVcY8nfw1xPzvI6qZGSKiVlOYQBWWYA-NbgePKk425xorOzUFoKDiR_-UlhZko1cf71EICoIVkwpcYNs8b4bBbVznj-VguA1GkKqlZZkYBZvO1RTiCwuiT6jO_6420_J3GYdECQZQOWNClohwn4pWbYtC4uh_5zj8GJrcyyzaNXhqesRCpLcqMdxwMrDDK4ha9R-VSAi_SAkBlg8KND7_pAY9RBFzLzOvzFQUY1xZ7PmnaXGl-VlY_tEebI4ilm0qN27-iK5IC1zRrWW9KYX1hKBXSi2-a97DqcoCspwhhrDs9W1JcaLeOCCJpTI2R8AdPdXrFCo3jyfrqsYWF7OogRDMrcLN9DTAJS-UlpD43FKDc_MH98w0q1iMyII_FEVhAToPJg8nr30T4bf6i0i5SAVzP0PVBb5zkv7MToceRKvZyO4XxrcuSYx0ebtvJKgyLC2ZGM4bSaL42Lq9JodQkr8s|n0MROF_8mEHJ9AOIfFvcEs5rS7w";
//        String cookieName2 = "ph_phc_t3lgBB66QsPW4HEfiGopO14um4XGNtBcefEKYWelWda_posthog";
//        String cookieValue2 = "%7B%22distinct_id%22%3A%22019036e4-b3e0-7d53-87ba-b954faf89bb1%22%2C%22%24device_id%22%3A%22019036e4-b3e0-7d53-87ba-b954faf89bb1%22%2C%22%24user_state%22%3A%22anonymous%22%2C%22%24session_recording_enabled_server_side%22%3Afalse%2C%22%24autocapture_disabled_server_side%22%3Atrue%2C%22%24active_feature_flags%22%3A%5B%5D%2C%22%24enabled_feature_flags%22%3A%7B%7D%2C%22%24feature_flag_payloads%22%3A%7B%7D%2C%22%24stored_group_properties%22%3A%7B%22cluster%22%3A%7B%7D%7D%2C%22%24groups%22%3A%7B%22cluster%22%3A%2214d64932-a2ad-47db-be9f-5b77a310f3a0%22%7D%2C%22%24sesid%22%3A%5B1719169487721%2C%220190467c-936a-7e64-aa0a-3a37cc49a824%22%2C1719169487721%5D%7D";
//        String cookieName3 = "AADSSO";
//        String cookieValue3 = "NA|NoExtension";
//        String cookieName4 = "CCState";
//        String cookieValue4 = "Q29zQkNoaDJZWEprWVdGdUxtUjFZVUJ6Y0hKcGJtdHNjaTVqYjIwU0FRRWlDUW1BdVpmMUIzN2NTQ29KQ1FQOGZYV1VtZHhJTWlvS0Vnb1EwbG1seWF0NkUwK203ZWZweFNyc2h4SUpDWEpodlprVGxOeElHZ2tKQTN4Y3piaVIzRWc0QUVnQVVoSUtFQ0ordkZJeFpvaE1uRStjRVZ3WVlVaGFFZ29RSWt0aWNqeW1KRU9paWxKRmtYSjFJUklTQ2hDWExQcDVLcm03UzRHR2JqNFB6aXE5R2drSkEzeGN6YmlSM0VneWZnSUFBUjhCQUFBQUtVOENaczE2blVlQVRkcG9vWGtER0FJQTdQOEZBUFQvYnF0MzM4QUR1ekRPUVdFempXOUMvMkd2aDAyZHVWUy8rOEJlT3BrdGQ5dFZib3p4bkhtWElZTmtRSVMrcm1lQkl6bDZYK0kwK1JEMGU2U21ZeTNGYVBGVXpTTk5Hd1dXSjVXa29tSkY3UTgzZDA5WW9BVm1DSzd4OEFhMG1RPT0=";
//        String cookieName5 = "ESTSAUTH";
//        String cookieValue5 = "0.AScAIn68UjFmiEycT5wRXBhhSHyDmdnF9e9DiwL96rT3gI8AAS4.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P_lClk3vBaqzKJtrZzGyuyNbroiIaFx1xrXvN64ksp3v9rrPjtRi2YUoXTsjM7fcFj-7f3lN1DOMg";
//
//        String cookieName6 = "ESTSAUTHLIGHT";
//        String cookieValue6 = "+2fdfe1f2-3f1d-4bee-bcfa-e3c280ca4af1";
//        String cookieName7 = "ESTSAUTHPERSISTENT";
//        String cookieValue7 = "0.AScAIn68UjFmiEycT5wRXBhhSHyDmdnF9e9DiwL96rT3gI8AAS4.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P9_hbzyqcSWQLB8tifJgVevIaR1R5vnM-pBLEg7-xGB3uA7S-GPvaBMsbWfDngVmEh7CI0KsLqnSQBtrye_YSuxPk3d3PH81W5Vhfb9Y8SPOK-3RII3YiNRrngiX1NCHwlb9wKYQu2HYaeEvg5l94ni1QIAW2WuljK5BzV0vgO-x8_UOoI17bKlz5Rwzs8PsedKidtovgcTwGdTAzKwDxpv8ZTOmzgEOluTZK-j7rVd-4eeqgf9XrBlhFVC3X17hPJB1vOTfmywIXhunwoSHunzuVZi8vZateXrRZLCispEztobJEDO1XwL-1Xlez6UiSi38i8T-fHg5tQ7BP2sIvNJfUTbsf5dYNGlPdYEwS_QlTlWQg8FuyKHwhKAZtBxn_m7NGyRqVre916cs5pIAt9t51-cLcW2x8sEWVf6xP1wngqsgWbgqSnhid7qKAbaZWYgXB16MDbyJiTa_wUNk3VTmwF1D9YcTaJpmzoEh7g9RL2QwJPnHRZatydupcgHFuNa21WD3zN49zEJ_fx4eN_eD9WDj_30MsLNgTvS8HqOnFjhzB_E5H-GCl2nFcv3Fhk3ASM-wo4g9xc6FP2Rd0WkAcGbdyasnvkBIIC1MHpcRE8bNqaGUw4ci9_1B0xzIQojIvnFu5Iwr4_LVgFlUBbSmQVGgS5roCR1MPFsUO7tiAY5W3fMguWmTQ";
//        String cookieName8 = "MSFPC";
//        String cookieValue8 = "723c0b1f208f47eca9bc18af4d6922ac&HASH=723c&LV=202406&V=4&LU=1718950207665";
//        String cookieName9 = "MicrosoftApplicationsTelemetryDeviceId";
//        String cookieValue9 = "e6a1b15f-de27-4f6f-aef7-aa583b876762";
//        String cookieName10 = "SignInStateCookie";
//        String cookieValue10 = "CAgABFgIAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P_dpDCVbelfaSctUxuU-blpwoN18lIbOBCWkz-cUUZLkGNLP40qvrtXNyZ2zA6j6evdLPffsz4sl7zAhpltPpT_oXnw1boKaTja8qIOdembpyPaXofzpFj2E6HGRKKb37vTNBakLrhUNUtkt__cnt04J1Mv4QeRLVFzJKFbrGA1AQLTcyqzaNAoNtADdJXmRFyO2tEaLiZYyv0mCeJGtquHwjxoFQO3spYMPutFUPYKoXuwnEPpjpnVY28lwd0QpEK9alI2IdRzTwOUw6qTpmkpit07eLsczFt7SI-Lf-XXTsJgTXzyfBl9IW_FddARLZ3SCVO-eIQuiPu3kk_LdEzvzd7yO_npBw8VU6vMqhPaSeRp9AcWIGlTAlZBr0w_uGLTChPKsla6Dun8sMSjg4kVCiClR3IoNThyjcBqmVwp01z2CplCZRAGWeXGVNg7ZFhyT3GujJ_u26EN3tVfySsA70pDX9zj2SQyv8iPZHOOHCnxUeZamyxynRBIIYOn8nE3fymatlzdxkMdU47uqUXAN_OhMGdL7LERE4RVG3Xi_Ra2nlOm88iHBCLQFN5mbvl5OnF5OYrQuZkNwkTVzcUHw_LUIXJ_bQ8UKSnr12n4RNS7HSR65xP5RSSuBgAxvISuH4ZAEUe5swCEt1IdBsjkta22Sln_Qpi8_lQ9vA";
//        String cookieName11 = "brcap";
//        String cookieValue11 = "0";
//        String cookieName12 = "buid";
//        String cookieValue12 = "0.AScAIn68UjFmiEycT5wRXBhhSHyDmdnF9e9DiwL96rT3gI8AAS4.AQABGgEAAAApTwJmzXqdR4BN2miheQMYet-AJhH6wcKM82_DFyZl2W-J3wsnXejHewlKqDIv66ux-7dvMPi7ifO2SBWi5oytRdkUWrgHce1EUflsAV6NyA9r60wfq55YfKKUZaK_V5ST4TJ_iilWi_Bv05oDDolioE8UFIoN9F3Ca_ZPH8FSsfpTg_GoWohsPh0wJpyzcm4gAA";
//        String cookieName13 = "esctx";
//        String cookieValue13 = "PAQABBwEAAAApTwJmzXqdR4BN2miheQMY-ov2iBQh3X2VZwfb4xjS06eAjT6NJBsk7iq1igRcjH0J4WguIhUELLTq2aXsi0tf1MzebYdavKXlWnYUE82vIhUHoRR4UfXAv9chptr66_aIef2JM2CSnl7gRRXTqZrrnAcHAQ_cFvnNFemIYPS8aqsZDeeSGyzc7pgT8rXZrUMgAA";
//        String cookieName14 = "fpc";
//        String cookieValue14 = "AmCwn_tzCXtLuz0VTIur7dcIhg5JAgAAAM0QC94OAAAA";
//        String cookieName15 = "stsservicecookie";
//        String cookieValue15 = "estsfd";
//        String cookieName16 = "x-ms-gateway-slice";
//        String cookieValue16 = "estsfd";
//
//
//        connection.setRequestProperty("Cookie",   cookieName1 + "=" + cookieValue1 + "; "
//                                                + cookieName2 + "=" + cookieValue2 + "; "
//                                                + cookieName3 + "=" + cookieValue3 + "; "
//                                                + cookieName4 + "=" + cookieValue4 + "; "
//                                                + cookieName5 + "=" + cookieValue5 + "; "
//                                                + cookieName6 + "=" + cookieValue6 + "; "
//                                                + cookieName7 + "=" + cookieValue7 + "; "
//                                                + cookieName8 + "=" + cookieValue8 + "; "
//                                                + cookieName9 + "=" + cookieValue9 + "; "
//                                                + cookieName10 + "=" + cookieValue10 + "; "
//                                                + cookieName11 + "=" + cookieValue11 + "; "
//                                                + cookieName12 + "=" + cookieValue12 + "; "
//                                                + cookieName13 + "=" + cookieValue13 + "; "
//                                                + cookieName14 + "=" + cookieValue14 + "; "
//                                                + cookieName15 + "=" + cookieValue15 + "; "
//                                                + cookieName16 + "=" + cookieValue16
//        );
//
//        // Get response
//        int responseCode = connection.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//
//        // Read response body
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        // Print response
//        System.out.println("Response Body:");
//        System.out.println(response.toString());
//
//        // Disconnect the connection
//        connection.disconnect();
//    }
//}
