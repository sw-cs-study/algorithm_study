function solution(diffs, times, limit) {
  const check = (level) => {
    // level로 limit 통과할 수 있는지 여부 return
    let result = 0;
    diffs.forEach((diff, i) => {
      if (diff <= level) {
        result += times[i];
      } else {
        result += times[i] + (diff - level) * (times[i] + times[i - 1]);
      }
    });
    if (result > limit) return false;
    return true;
  };

  let [l, r] = [0, 100000];
  while (l + 1 < r) {
    let mid = Math.floor((l + r) / 2);

    if (check(mid)) r = mid;
    else l = mid;
  }

  return r;
}
