using MongoDB.Bson;

namespace Models
{
    public class GuideDetail
    {
        public ObjectId Id { get; set; }
        public long SNo { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string Gender { get; set; }
        public string DateOfBirth { get; set; }
        public string LicenceNo { get; set; }
        public string Validity { get; set; }
        public string Email { get; set; }
        public long Mobile { get; set; }
        public string Languages { get; set; }
        public string Region { get; set; }
        public string CityOperational { get; set; }
    }
}
