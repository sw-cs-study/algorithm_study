/** 
b가 최대한 많이 훔치는 순서로 진행하면서 a가 걸리지 않는 수를 찾기
내림차순으로 정렬 후 앞에 번호부터 최대한 많이 b가 가져가기 (dfs)
그렇게 가져가고 남은 번호를 a가 가져가며 답이 나오는데 체크
*/

function solution(info, n, m) {
  var answer = Infinity;
  // 이미 방문한 경우의 수는 패스하기
  let visited = [...Array(121)].map(() =>
    [...Array(121)].map(() => Array(info.length).fill(false)),
  );

  const dfs = (a = 0, b = 0, i = 0) => {
    // 방문했는지 체크
    if (visited[a][b][i]) return;
    visited[a][b][i] = true;
    // 끝에 도달하면 a 흔적 비교
    if (i === info.length) {
      answer = Math.min(a, answer);
      return;
    }

    let newA = a + info[i][0];
    let newB = b + info[i][1];

    if (newA < n) dfs(newA, b, i + 1);
    if (newB < m) dfs(a, newB, i + 1);

    return;
  };

  dfs();

  return answer === Infinity ? -1 : answer;
}
