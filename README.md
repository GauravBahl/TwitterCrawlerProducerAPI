# TwitterCrawlerProducerAPI

API ENDPOINT: http://localhost:8098/producer/initiate 

POST: BODY
{
	"consumer_key": "TWITTER_KEY",
  "consumer_secret": "TWITTER_CON_SECRET",
  "token": "TWITTER_TOKEN",
  "token_secret": "TWITTER_SECRET",
  "kafka_topic":"gaurav",
	"twitter_keywords":[
		{
		"keywords_list":["Trump"]
		}
	]
	
}
