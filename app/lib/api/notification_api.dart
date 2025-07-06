import '../utils/request.dart';

class NotificationApi {
  static Future<int> getUnreadCount() async {
    final res = await Request.instance.get('/notification/unreadCount');
    return res.data['count'] ?? 0;
  }
}
