extension StringUtils on String {
  String left(int count) {
    if (count <= 0) return '';
    return length >= count ? substring(0, count) : this;
  }

  String right(int count) {
    if (count <= 0) return '';
    return length >= count ? substring(length - count) : this;
  }
  String mid(int start, int count) {
    if (start < 0 || count <= 0 || start >= length) return '';
    final end = (start + count <= length) ? start + count : length;
    return substring(start, end);
  }
}
