using MongoDB.Bson;

namespace Models
{
    public class User
    {
        public ObjectId Id { get; set; }
        public long MobileNumber { get; set; }
        public string Password { get; set; }
        public bool IsGuide { get; set; }
        public string Name { get; set; }
        public string LicenceNo { get; set; }
        public bool IsOnline { get; set; }
        public bool IsAvailable { get; set; }
    }
}
