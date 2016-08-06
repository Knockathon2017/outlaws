using MongoDB.Bson;

namespace Models
{
    public class GuideRating
    {
        public ObjectId Id { get; set; }
        public ObjectId UserId { get; set; }
        public int Rating { get; set; }
        public long CountRatings { get; set; }
    }
}
