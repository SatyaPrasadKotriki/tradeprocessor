# Trade Processor

Currently we have two rest api's for processing trades and fetching trades in the system.

Trade processing system has been taken care with the following validations (in future we can add/enhance the validators) 
1.	During transmission if the lower version is being received by the store it will reject the trade and throw an exception. If the version is same it will override the existing record.
2.	Store should not allow the trade which has less maturity date then today date.

Scheduler should automatically update the expire flag if in a store the trade crosses the maturity date.

Trade store is currently using H2 in-memory db stored in a file. 

