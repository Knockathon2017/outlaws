using MongoDB.Bson;

namespace Models
{
    public class GuideRatingDetail
    {
        public ObjectId Id { get; set; }
        public ObjectId GuideId { get; set; }
        public int Rating { get; set; }
        public string Comment { get; set; }
        public ObjectId TouristId { get; set; }
    }
}
