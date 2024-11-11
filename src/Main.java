import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static int N, M;
    static int[][] map;
    static boolean[][][] isVisited;  // [x][y][벽 부숨 여부]로 방문 상태 저장
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] value = bf.readLine().split(" ");

        N = Integer.parseInt(value[0]);
        M = Integer.parseInt(value[1]);

        map = new int[N][M];
        isVisited = new boolean[N][M][2];  // 벽을 부수지 않은 경우와 부순 경우를 구분

        for (int i = 0; i < N; i++) {
            String str = bf.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }

        System.out.println(bfs(0, 0));
    }

    public static int bfs(int x, int y) {
        Queue<int[]> que = new LinkedList<>();
        que.offer(new int[] {x, y, 0, 1});  // x, y, 벽 부숨 여부, 거리
        isVisited[x][y][0] = true;  // 시작점은 벽을 부수지 않은 상태에서 방문 처리

        while (!que.isEmpty()) {
            int[] arr = que.poll();
            int a = arr[0];
            int b = arr[1];
            int wallBroken = arr[2];
            int distance = arr[3];

            // 목적지 도달
            if (a == N - 1 && b == M - 1) {
                return distance;
            }

            for (int i = 0; i < 4; i++) {
                int qx = a + dx[i];
                int qy = b + dy[i];

                if (qx >= 0 && qy >= 0 && qx < N && qy < M) {
                    // 벽을 만나고 벽을 부수지 않은 상태인 경우
                    if (map[qx][qy] == 1 && wallBroken == 0 && !isVisited[qx][qy][1]) {
                        que.offer(new int[] {qx, qy, 1, distance + 1});
                        isVisited[qx][qy][1] = true;
                    }
                    // 벽이 아니고 해당 상태로 방문한 적이 없는 경우
                    else if (map[qx][qy] == 0 && !isVisited[qx][qy][wallBroken]) {
                        que.offer(new int[] {qx, qy, wallBroken, distance + 1});
                        isVisited[qx][qy][wallBroken] = true;
                    }
                }
            }
        }
        return -1;  // 목적지에 도달할 수 없는 경우
    }
}
