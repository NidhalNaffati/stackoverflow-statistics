using Newtonsoft.Json;
using stackoverflow_statistics.Models;

namespace stackoverflow_statistics.Dto

{
    [JsonObject(ItemNullValueHandling = NullValueHandling.Ignore)]
    public class StackExchangeResponse
    {
        [JsonProperty("items")]
        public List<Question> Items { get; set; }

        [JsonProperty("has_more")]
        public bool HasMore { get; set; }

        [JsonProperty("quota_max")]
        public int QuotaMax { get; set; }

        [JsonProperty("quota_remaining")]
        public int QuotaRemaining { get; set; }
    }
}